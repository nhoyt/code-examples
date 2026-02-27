/*  params.js  */

let t1 = 'https://example.org/page?abc=123&def=456&s=something';
let t2 = 'https://www.google.com/search?q=waterman+fountain+pens&oq=wat&gs_lcrp=EgZjaHJvbWUqBggA&sourceid=chrome&ie=UTF-8';

// let params = [ 'q', 'query', 's', 'search' ];
// let params = '  q ,   query,   s   ,   search  '.split(/\s*,\s*/);
let params = ' ,   ,   q ,  ,, query,   s   ,   search  ,,,  ';

//  input: string with comma-separated parameters
//  return: array of parameters
function processInput (input) {
  let result = input, i = 0;

  function log () {
    console.log(`${++i})`, `'${result}'`)
  }
  function removeWhitespaceChars (s) {
    return s.replace(/\s+/g, '');
  }
  function collapseCommas (s) {
    return s.replace(/,{2,}/g, ',');
  }
  function removeCommasAtStartOrEnd (s) {
    return s.replace(/^,|,$/g, '');
  }

  log(); // initial state
  result = removeWhitespaceChars(result); log();
  result = collapseCommas(result); log();
  result = removeCommasAtStartOrEnd(result); log();
  return result.split(',');
}

function cleanUrl (url, retainList) {
  console.log();
  console.log('url:', url.toString());
  console.log('list:', retainList.toString());
  const params = new URLSearchParams(url.searchParams);
  for (const name of params.keys()) {
    // console.log('name: ', name);
    if (!retainList.includes(name)) {
      console.log('deleting', name);
      url.searchParams.delete(name);
    }
  }

  console.log('end result:', url.toString());
}

let list = processInput(params);
cleanUrl(new URL(t1), list);
cleanUrl(new URL(t2), list);
