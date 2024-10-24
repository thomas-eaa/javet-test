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

## Workaround

In this branch, `serverrender.js` is bundled together with `entry-server.tsx`. It can be executed by Javet if you fix the following imports in `dist/serverrender.js`:

From:
```javascript
import require$$1 from "stream";
import require$$0 from "util";
```

To:
```javascript
import require$$1 from "node:stream";
import require$$0 from "node:util";
```

This can also be accomplished with the following implementation in V8ModuleResolver:

```java
nodeRuntime.setV8ModuleResolver(new JavetBuiltInModuleResolver() {
    @Override
    public IV8Module resolve(V8Runtime v8Runtime, String resourceName, IV8Module v8ModuleReferrer)  throws JavetException { 
        if (resourceName.equals("stream") || resourceName.equals("util")) {
            resourceName = "node:" + resourceName;
        }
        return super.resolve(v8Runtime, resourceName, v8ModuleReferrer);
    }
});
```

Although the execution is working in principal, the dynamic import (`entry-server.tsx:4`: by React's `lazy()` function) cannot be resolved by Javet, so the output looks like this:

```html
<h1>My App</h1><!--$?--><template id="B:0"></template><div>Loading...</div><!--/$--><script>function $RX(b,c,d,e){var a=document.getElementById(b);a&&(b=a.previousSibling,b.data="$!",a=a.dataset,c&&(a.dgst=c),d&&(a.msg=d),e&&(a.stck=e),b._reactRetry&&b._reactRetry())};$RX("B:0","","A dynamic import callback was not specified.","\n    at Lazy\n    at Suspense\n    at App")</script>
```