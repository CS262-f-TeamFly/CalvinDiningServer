const express = require('express');
const moment = require('moment-timezone');
const cacheResponseDirective = require('express-cache-response-directive');

var app = express();

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.use(cacheResponseDirective());

function mealMenu (name, startTime, endTime, description) {
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
}

function mkeMmt (time) {
    return moment.tz(time, "hh:mm a", "America/Detroit");
}

var todaysMenu = [{"name": "Commons",
    "events": [
    new mealMenu('breakfast', mkeMmt('8:30 a'), mkeMmt('10:00 a'), 'contenental breakfast\nfdadffdas\ndasfdsafadsdfsa\nsdafsdafasdfda\nasdfasdfdasfddsfaasdf\nasdfsadfsadfsadf'),
    new mealMenu('lunch', mkeMmt('11:30 a'), mkeMmt('1:30 p'), 'lunch\ndafsadfasdffsadfsadfd\nsadfdsafsadasdfdasfdasfdsafasdf\nsadfsadfasfsadasdfasdfadsfds\ndasgasdfdasasddasfdas\nasdfasdfasdfs'),
    new mealMenu('dinner', mkeMmt('4:45 p'), mkeMmt('6:15 p'), 'dinner\nasdfsadfsdfasdfsdadsfadadf\nasdfasdfasfdsafdfdsa\nadsfasfdasddfsdafdsafdsaf\nasdfdasfsdafdsdadfsdafdadffsaddfsasdffdasdfdsfadsasdfadsfsad\ndfasdfa\nasdfads\nasdfasdfsad\nasedfsdfsd\nasdfsadfdsa\nasdfasdfdsa\nsadfasdfasddfasd\nasdfsadfdsa\nasdfsdafasdfasd\nasdfsadfasddfasd\nasdfasddfsaf\nasdfsaddfdsa\nasdfasdfsadfas\nasdfasdfasdfasdfdas\nasdfdasfdsadfsadfasdf'),
]},
    {"name": "Knollcrest",
    "events": [
    new mealMenu('breakfast', mkeMmt('8:30 a'), mkeMmt('10:00 a'), 'KNOOOOOOOOOOOOOOOOOOOOOOOOOLLLLL CREST!!!!!!!!'),
    new mealMenu('lunch', mkeMmt('11:30 a'), mkeMmt('1:30 p'), 'KNOLLLW'),
    new mealMenu('dinner', mkeMmt('4:45 p'), mkeMmt('6:15 p'), ':)'),
]}];

// send today over
app.get('/today', function (req, res) {
    res.cacheControl({maxAge: "2h"});
    res.send(todaysMenu);
});

module.exports = app;