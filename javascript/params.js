/*  params.js  */

let t1 = 'https://example.org/page?abc=123&def=456&s=something';
let t2 = 'https://www.google.com/search?q=waterman+fountain+pens&oq=wat&gs_lcrp=EgZjaHJvbWUqBggA&sourceid=chrome&ie=UTF-8';
let list = [ 'q', 'query', 's', 'search' ];

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

cleanUrl(new URL(t1), ['s']);
cleanUrl(new URL(t2), list);
