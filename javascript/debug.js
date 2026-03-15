/*  debug.js  */

const logToConsole = true;

function traverseObject (obj) {
  const output = [];
  for (const prop in obj) {
    if (typeof obj[prop] === 'object') {
      const inner = obj[prop];
      output.push(`${prop}: { ${traverseObject(inner).join(', ')} }`);
      ;
    }
    else {
      output.push(`${prop}: '${obj[prop]}'`);
    }
  }
  return output;
}

export const debug = {
  log: (...args) => {
    if (logToConsole) {
      console.log(args.join(' '));
    }
  },

  logObj: (obj, ...args) => {
    if (logToConsole) {
      const objInfo = traverseObject(obj);
      const prefix = args.length > 1 ? args.join(': ') : args[0];
      console.log(`${prefix}: ${objInfo.join(', ')}`);
    }
  }
};

//  testing...
const displaySettings = {
  html:       true,
  latex:      false,
  markdown:   true,
  inner:      {
    p1:       27,
    p2:       87,
    farIn:    {
      p3:     42,
      p4:     102,
    }
  },
  mediawiki:  false,
  titleurl:   true,
  xml:        false
}

const options = {
  display:    displaySettings,
  time:       '3.5',
  link:       'site',
  href:       'href',
  name:       'name'
}

debug.logObj(options, 'options');
