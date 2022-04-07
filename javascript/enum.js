class Enum {
  constructor (list, startVal) {
    let start = startVal ? parseInt(startVal, 10) : 1;
    for (const item of list) {
      this[item] = start++;
    }
    Object.freeze(this);
  }
}

var Color = new Enum(['RED', 'GREEN', 'BLUE'], 4);

var Views = new Enum(['Summary', 'Structure', 'Layout', 'Content', 'Links',
                      'Forms', 'Widgets', 'Tables', 'Images', 'Media']);
Views.Summary = 12;
delete Views.Media;
Views.ABC = 123;

console.log(Color);
console.log(Views);
