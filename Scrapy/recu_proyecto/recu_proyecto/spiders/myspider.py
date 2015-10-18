from scrapy.contrib.spiders import CrawlSpider, Rule
from scrapy.contrib.linkextractors.sgml import SgmlLinkExtractor
from scrapy.selector import HtmlXPathSelector
from recu_proyecto.items import RecuProyectoItem

class MySpider(CrawlSpider):
    name = "example"
    allowed_domains = ["sfbay.craigslist.org"]
    start_urls = ["http://sfbay.craigslist.org/search/npo"]

    rules = (
        Rule(SgmlLinkExtractor(allow=(), 
		restrict_xpaths=('//a[@class="button next"]',)),
		callback="parse_items", 
		follow= True),
    )

    def parse_items(self, response):
		hxs = HtmlXPathSelector(response)
		titles = hxs.xpath('//span[@class="pl"]')
		items = []
		for titles in titles:
			filename = response.url+'.html'
			open(filename, 'wb').write(response.body)
			item = RecuProyectoItem()
			item["title"] = titles.xpath("a/text()").extract()
			item["link"] = titles.xpath("a/@href").extract()
			items.append(item)
		return(items)
