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


function Venue (name, events) {
    this.name = name;
    this.events = events = [];
}

Venue.prototype.addEvent = function (event) {
    this.events.push(event);
}


function meal (name, startTime, endTime, description) {
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
    let requestDate = getDate();
    console.log("Requesting date: " + requestDate);
    let venueJSON = "https://calvin.edu/api/content/render/false/type/json/query/+structureName:DiningMenus%20+DiningMenus.venue:" + venueName + "%20+DiningMenus.date:" + requestDate + "%20+live:true/orderby/DiningMenus.startTime%20asc";
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
                    resolve(makeVenueFromJSON(vJson, venueName));
                } catch (e) {
                    reject(e);
                    console.log(e.message);
                }
            });
            // end data handling
        }).on('error', reject);
    });
}

function makeVenueFromJSON (vJson, venueName) {
    if (vJson.contentlets.length > 0) {
        let newVenue = new Venue(venueName, null); 
        for (var i = 0; i < vJson.contentlets.length; i++) {
            let vData = vJson.contentlets;
            let startTime = vData[i].startTime;
            let endTime = vData[i].endTime;
            let name = vData[i].mealName;
            let description = vData[i].menuContent;
            newVenue.addEvent(new meal(name, startTime, endTime, description));
        }
        console.log("THE DATA IS:", newVenue);
        return newVenue;
    } else {
        console.log("NO MENUES TODAY");
    }
}

// Copied from calvin's server
function getDate () {
    var m_names = new Array("", "Jan.", "Feb.", "Mar.",
        "Apr.", "May", "June", "July", "Aug.", "Sep.",
        "Oct.", "Nov.", "Dec.");

    // TODO use moment time zone
    var d = new Date();
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1;
    var curr_year = d.getFullYear();

    /* Edit: 12/01/2015, jlaughli 
       We need a two digit value for both date and month, 
       otherwise the JSON request fails. */
    var req_date = ("0" + d.getDate()).slice(-2);
    var req_month = ("0" + (d.getMonth() + 1)).slice(-2);
    var requestDate = curr_year + "" + req_month + "" + req_date;
    return requestDate;
}
