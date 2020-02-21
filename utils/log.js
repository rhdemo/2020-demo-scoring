const pino = require('pino');
const env = require('env-var');

const level = env.get('LOG_LEVEL', 'info').asEnum([
  'trace',
  'debug',
  'info',
  'warn',
  'error'
]);

/**
 * Creates a pino logger instance with a globally configured log level
 * @param {String} name
 */
module.exports = function getLogger (name) {
  return pino({
    level,
    name
  })
};
