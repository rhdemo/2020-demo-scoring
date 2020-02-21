'use strict';

module.exports = async function (fastify, opts) {
  fastify.get('/status', async function (request, reply) {
    return {'status': 'OK'}
  })
};
