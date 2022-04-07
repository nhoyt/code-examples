from urlparse import urlparse

url = 'http://abc.def.www.ibm.com:8080/index.html'

obj = urlparse(url)

domain = obj.netloc.split(':')[0]
print domain
components = domain.split('.')
print components
print '.'.join(components[1:])
