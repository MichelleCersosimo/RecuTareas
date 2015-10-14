import scrapy

class MySpider(scrapy.Spider):
	name         = "example"
	allowed_domains    = ["dmoz.org"]
	start_urls    = ["http://www.dmoz.org/Computers/Programming/Languages/Python/Books/"]
 
	def parse(self, response):
		filename = response.url.split("/")[-2] + '.html'
		open(filename, 'wb').write(response.body)
		#with open(filename, 'wb') as f:
		#	f.write(response.body)