import requests
from bs4 import BeautifulSoup


x=input("Enter the user acount name on Codechef.")

url = 'https://www.hackerrank.com/'
url+=x  
#default settings to be added whenever doing this
response = requests.get(url, headers={'User-agent': 'Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36'})

html = response.content

soup = BeautifulSoup(response.content,'html.parser')
all_stars=soup.find_all("svg",class_ ="badge-star")#the star on an account
i=0
result=[]
for th in all_stars:
    result.extend(th.find_all(text='svg'))
    #not quoite suer about this part, something to do with the op of the find_all method being a list, so go through it only via for loop
    i=i+1
print( 'There are',i,'stars on this account')