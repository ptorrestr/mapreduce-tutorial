import scrapy
import json
import re

base = "./"

class AuthorSpider(scrapy.Spider): 
    name = 'author'
    start_urls = ['http://quotes.toscrape.com/']
    def parse(self, response):
        # follow links to author pages
        for href in response.css('.author+a::attr(href)').extract():
            yield scrapy.Request(response.urljoin(href), 
                                 callback=self.parse_author)
        # follow pagination links
        next_page = response.css('li.next a::attr(href)').extract_first() 
        if next_page is not None:
            next_page = response.urljoin(next_page)
            yield scrapy.Request(next_page, callback=self.parse)
            
    def parse_author(self, response): 
        def extract_with_css(query):
            return response.css(query).extract_first().strip()
        
        def urlify(s):
            s = re.sub(r"[^\w\s]", '', s)
            s = re.sub(r"\s+", '-', s)
            return s

        data = {
            'name': extract_with_css('h3.author-title::text'), 
            'birthdate': extract_with_css('.author-born-date::text'), 
            'bio': extract_with_css('.author-description::text'),
            }
        with open(base + "/" + urlify(data["name"]) + ".json", "w+") as f:
            f.write(json.dumps(data))
