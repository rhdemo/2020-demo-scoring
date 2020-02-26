'use strict';

const path = require('path');
const AutoLoad = require('fastify-autoload');
const log = require('./utils/log')('scoring-server');
const {kafkaProducer} = require("./kafka/producer");

const opts = {};
const {PORT, IP} = require('./utils/constants');
const fastify = require('fastify')();


//---------------------
// Fastify Plugins

//---------------------
// Custom Plugins
fastify.register(AutoLoad, {
  dir: path.join(__dirname, 'plugins'),
  options: Object.assign({}, opts)
});

//---------------------
// Decorators

//---------------------
// Hooks and Middlewares

//---------------------
// Services
fastify.register(AutoLoad, {
  dir: path.join(__dirname, 'services'),
  options: Object.assign({}, opts)
});

fastify.listen(PORT, IP, function (err, address) {
  if (err) {
    log.error(err);
    process.exit(1);
  }
  log.info(`server listening on ${address}`);
});


