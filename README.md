# Javet Test

## Setup

In the `scripts` folder, run `yarn`, then `yarn build`.
To render the SSR output run `yarn render`, the output should be something like this:

```html
<h1>My App</h1><!--$?--><template id="B:0"></template><div>Loading...</div><!--/$--><div hidden id="S:0"><div class="detail"><ul><li>Item number <!-- 
-->0</li><li>Item number <!-- -->1</li><li>Item number <!-- -->2</li><li>Item number <!-- -->3</li><li>Item number <!-- -->4</li><li>Item number <!-- 
-->5</li><li>Item number <!-- -->6</li><li>Item number <!-- -->7</li><li>Item number <!-- -->8</li><li>Item number <!-- -->9</li></ul></div></div><scr
ipt>function $RC(a,b){a=document.getElementById(a);b=document.getElementById(b);b.parentNode.removeChild(b);if(a){a=a.previousSibling;var f=a.parentNo
de,c=a.nextSibling,e=0;do{if(c&&8===c.nodeType){var d=c.data;if("/$"===d)if(0===e)break;else e--;else"$"!==d&&"$?"!==d&&"$!"!==d||e++}d=c.nextSibling;
f.removeChild(c);c=d}while(c);for(;b.firstChild;)f.insertBefore(b.firstChild,c);a.data="$";a._reactRetry&&a._reactRetry()}};$RC("B:0","S:0")</script> 

```

## Goal

The same output as with node should be delivered by Javet in the `JavetTest` class, so SSR can be done in the Java/Spring Boot backend. Ideally, the output of the render function will be streamed back to Java in order to hand it over to STDOUT or an HTTP response in a non-blocking mode. 

