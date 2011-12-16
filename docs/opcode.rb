require 'rubygems'
require 'mechanize'

def toHexString(aInt)
  out = aInt.to_s(16).upcase;
  while out.length < 2
    out = "0" + out
  end

  return "0x" + out
end

list = Array.new

a = Mechanize.new
page = a.get "http://www.pastraiser.com/cpu/gameboy/gameboy_opcodes.html"


i = -1
index = -1;
page.parser.xpath("//table/tr/td[@class='withborder']/text()").each do |t|
  if (t.text == "A (accumulator)") then
    break
  end

  if (index>255) then
    break
  end

  #puts "#{toHexString(index)} #{t.text}"
  i = i + 1
  
  if (i.modulo(3) == 0)
    #puts "================="
    index = index + 1;
  end

  list.push t.text

end

i=0
index=0
while i < list.length-1
  puts toHexString(index) + " XXX " + list[i+1] + " XXX " + list[i+2] + " XXX " + list[i+3]
  i = i + 3;
  index = index + 1
end
