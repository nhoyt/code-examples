/*  debugLog.js  */

class DebugLogger {
  constructor (flag) {
    this.enabled = flag
  }

  log (...args) {
    if (this.enabled) {
      console.log(args.join(' '));
    }
  }

  // log object property-value pairs
  logObj (obj) {
    const output = [];
    for (const prop in obj) {
      output.push(`${prop}: ${obj[prop]}`);
    }
    if (this.enabled) console.log(output.join(', '));
  }

  // log key-value pairs inferred from args list
  logKV (...args) {
    /*
    if (args.length % 2) args = args.slice(0, args.length-1);
    if (this.enabled) {
      console.log('Omitting last arg!')
    }
    */
    const output = [], ns = 'NOT SPECIFIED';
    for (let i = 0; i < args.length; i += 2) {
      let value = typeof args[i+1] === 'undefined' ? ns : args[i+1];
      output.push(`${args[i]}: ${value}`);
    }

    if (this.enabled) console.log(output.join(', '));
  }
}

// testing

const debug = new DebugLogger(true);

let x = 1, y = 2, z = "three";

debug.logKV('x', x, 'y', y, 'z', z, 'a');

const myObj = {
  a: 4,
  b: 5,
  c: 'six',
  d: 'note'
}

debug.logObj(myObj);
