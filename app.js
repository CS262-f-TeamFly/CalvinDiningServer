const express = require('express');
const moment = require('moment-timezone');
var app = express();

app.get('/', function (req, res) {
  res.send('Hello World!');
});

function mealMenu (name, startTime, endTime, description) {
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
}

function mkeMmt (time) {
    return moment.tz(time, "hh:mm a", "America/Detroit");
}

var todaysMenu = [
    new mealMenu('breakfast', mkeMmt('8:30 a'), mkeMmt('10:00 a'), 'contenental breakfast\nfdadffdas\ndasfdsafadsdfsa\nsdafsdafasdfda\nasdfasdfdasfddsfaasdf\nasdfsadfsadfsadf'),
    new mealMenu('lunch', mkeMmt('11:30 a'), mkeMmt('1:30 p'), 'lunch\ndafsadfasdffsadfsadfd\nsadfdsafsadasdfdasfdasfdsafasdf\nsadfsadfasfsadasdfasdfadsfds\ndasgasdfdasasddasfdas\nasdfasdfasdfs'),
    new mealMenu('dinner', mkeMmt('4:45 p'), mkeMmt('6:15 p'), 'dinner\nasdfsadfsdfasdfsdadsfadadf\nasdfasdfasfdsafdfdsa\nadsfasfdasddfsdafdsafdsaf\nasdfdasfsdafdsdadfsdafdadffsaddfsasdffdasdfdsfadsasdfadsfsad\ndfasdfa\nasdfads\nasdfasdfsad\nasedfsdfsd\nasdfsadfdsa\nasdfasdfdsa\nsadfasdfasddfasd\nasdfsadfdsa\nasdfsdafasdfasd\nasdfsadfasddfasd\nasdfasddfsaf\nasdfsaddfdsa\nasdfasdfsadfas\nasdfasdfasdfasdfdas\nasdfdasfdsadfsadfasdf'),
];

// send today over
app.get('/today', function (req, res) {
    res.send(todaysMenu);
});

module.exports = app;