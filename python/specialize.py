class Super:
    def method(self):
        print 'in Super.method'       # default behavior
    def delegate(self):
        self.action()                 # expected to be defined

class Inheritor(Super):               # inherit method verbatim
    pass

class Replacer(Super):                # replace method completely
    def method(self):
        print 'in Replacer.method'

class Extender(Super):                # extend method behavior
    def method(self):
        print 'starting Extender.method'
        Super.method(self)
        print 'ending Extender.method'

class Provider(Super):                # fill in a required method
    def action(self):
        print 'in Provider.action'

if __name__ == '__main__':
    for C in (Inheritor, Replacer, Extender):
        print '\n' + C.__name__ + '...'
        C().method()

    print '\nProvider...'
    x = Provider()
    x.delegate()
