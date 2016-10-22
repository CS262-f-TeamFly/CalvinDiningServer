#!/usr/bin/env node
'use strict';
const expressHttpAutodetect = require('express-autoserve');

// If you followed the Express-4 migration guide or used
// express-generator, your app definition will be its own
// CommonJS module.
const app = require('./app');

expressHttpAutodetect(app);
