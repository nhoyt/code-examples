class props(object):
    def __init__(self, age=21):
        self._age = age
    def get_age(self): return self._age
    def set_age(self, age): self._age = age
    age = property(get_age, set_age, None, None)
