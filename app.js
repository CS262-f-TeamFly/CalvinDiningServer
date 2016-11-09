'use strict';

const express = require('express');
const moment = require('moment-timezone');
const cacheResponseDirective = require('express-cache-response-directive');
const https = require('https');

var app = express();

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.use(cacheResponseDirective());

// send today over
app.get('/venues', function (req, res) {
    res.cacheControl({maxAge: "2h"});
    venues.then(venues => {
        res.send(venues);
    });
});

var venues;
runMakeVenues();

function runMakeVenues() {
    venues = makeVenues();
}

setInterval( runMakeVenues, 1800000);

module.exports = app;


function Venue (name) {
    this.name = name;
    this.events = [];
}

Venue.prototype.addEvent = function (event) {
    this.events.push(event);
}


function Meal (name, startTime, endTime, description) {
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
}

function mkeMmt (time) {
    return moment.tz(time, "hh:mm a", "America/Detroit");
}

function makeVenues () {
    return Promise.all([
        makeVenue("Commons Dining Hall"),
        makeVenue("Knollcrest Dining Hall"),
    ]);
}

function makeVenue (venueName) {
    let requestDates = getDates();
    let newVenue = new Venue(venueName);
    return Promise.all(requestDates.map( (date) => getData(newVenue, date))).then(() => newVenue);
}

function getData (venue, date) {
    let venueJSON = "https://calvin.edu/api/content/render/false/type/json/query/+structureName:DiningMenus%20+DiningMenus.venue:" + venue.name + "%20+DiningMenus.date:" + date.format("YYYYMMDD") + "%20+live:true/orderby/DiningMenus.startTime%20asc";
    return new Promise( (resolve, reject) => {
        https.get(venueJSON, (res) => {
            /*
            console.log("statusCode:", res.statusCode);
            console.log("headers:", res.headers);*/

            // https://nodejs.org/api/http.html#http_http_get_options_callback
            // handle getting data
            let vJson;
            res.setEncoding('utf8');
            let rawData = '';
            res.on('data', (chunk) => rawData += chunk);
                res.on('end', () => {
                try {
                    vJson = JSON.parse(rawData);
                    resolve(addEventsFromJson(venue, vJson, date));
                } catch (e) {
                    reject(e);
                    console.log(e.message);
                }
            });
            // end data handling
        }).on('error', reject);
    });
}

function addEventsFromJson (venue, vJson, date) {
    for (var i = 0; i < vJson.contentlets.length; i++) {
        let vData = vJson.contentlets;
        let startTime = setToCurrentDate(vData[i].startTime, date);
        let endTime = setToCurrentDate(vData[i].endTime, date);
        let name = vData[i].mealName;
        let description = vData[i].menuContent;
        
        venue.addEvent(new Meal(name, startTime, endTime, description));
    }
}

function setToCurrentDate (data, date) {
    let time = moment.tz(data, "America/Detroit");
    time.year(date.year()).month(date.month()).date(date.day());
    return time;
}


function getDates () {
    //TODO use tomorrow later
    let today = moment().tz('America/Detroit');
    let tomorrow = today.clone().add(1, 'day'); // for later
    return [today, tomorrow];
}
