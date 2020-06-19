(window.webpackJsonp=window.webpackJsonp||[]).push([[18],{158:function(e,t,n){"use strict";n.r(t),n.d(t,"frontMatter",(function(){return c})),n.d(t,"metadata",(function(){return b})),n.d(t,"rightToc",(function(){return l})),n.d(t,"default",(function(){return p}));var a=n(2),r=n(9),i=(n(0),n(221)),o=n(224),c={id:"options-navigationBar",title:"Navigation Bar Options",sidebar_label:"Navigation Bar"},b={id:"options-navigationBar",isDocsHomePage:!1,title:"Navigation Bar Options",description:"The Navigation Bar is the area at the bottom of the screen containing Android's three navigation buttons: Back, Home and Recents.",source:"@site/api/options-navigationBar.mdx",permalink:"/react-native-navigation/api/options-navigationBar",editUrl:"https://github.com/wix/react-native-navigation/edit/master/website/api/options-navigationBar.mdx",sidebar_label:"Navigation Bar",sidebar:"api",previous:{title:"Modal Options",permalink:"/react-native-navigation/api/options-modal"},next:{title:"Overlay",permalink:"/react-native-navigation/api/options-overlay"}},l=[{value:"<code>visible</code>",id:"visible",children:[]},{value:"<code>backgroundColor</code>",id:"backgroundcolor",children:[]}],u={rightToc:l};function p(e){var t=e.components,n=Object(r.a)(e,["components"]);return Object(i.b)("wrapper",Object(a.a)({},u,n,{components:t,mdxType:"MDXLayout"}),Object(i.b)("p",null,"The Navigation Bar is the area at the bottom of the screen containing Android's three navigation buttons: Back, Home and Recents."),Object(i.b)("p",null,"An example of a dark navigation bar:"),Object(i.b)("img",{width:"30%",src:Object(o.a)("/img/navBar_black.png")}),Object(i.b)("br",null),Object(i.b)("br",null),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-js"}),"const options = {\n  navigationBar: {}\n};\n")),Object(i.b)("h3",{id:"visible"},Object(i.b)("inlineCode",{parentName:"h3"},"visible")),Object(i.b)("p",null,"Set the navigation bar visibility."),Object(i.b)("table",null,Object(i.b)("thead",{parentName:"table"},Object(i.b)("tr",{parentName:"thead"},Object(i.b)("th",Object(a.a)({parentName:"tr"},{align:null}),"Type"),Object(i.b)("th",Object(a.a)({parentName:"tr"},{align:null}),"Required"),Object(i.b)("th",Object(a.a)({parentName:"tr"},{align:null}),"Platform"))),Object(i.b)("tbody",{parentName:"table"},Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",Object(a.a)({parentName:"tr"},{align:null}),"boolean"),Object(i.b)("td",Object(a.a)({parentName:"tr"},{align:null}),"No"),Object(i.b)("td",Object(a.a)({parentName:"tr"},{align:null}),"Android")))),Object(i.b)("h3",{id:"backgroundcolor"},Object(i.b)("inlineCode",{parentName:"h3"},"backgroundColor")),Object(i.b)("p",null,"Set the navigation bar color. When a light background color is used, the color of the navigation bar icons will adapt to a dark color."),Object(i.b)("img",{width:"30%",src:Object(o.a)("/img/navBar_white.png")}),Object(i.b)("table",null,Object(i.b)("thead",{parentName:"table"},Object(i.b)("tr",{parentName:"thead"},Object(i.b)("th",Object(a.a)({parentName:"tr"},{align:null}),"Type"),Object(i.b)("th",Object(a.a)({parentName:"tr"},{align:null}),"Required"),Object(i.b)("th",Object(a.a)({parentName:"tr"},{align:null}),"Platform"),Object(i.b)("th",Object(a.a)({parentName:"tr"},{align:null}),"Default"))),Object(i.b)("tbody",{parentName:"table"},Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",Object(a.a)({parentName:"tr"},{align:null}),"Color"),Object(i.b)("td",Object(a.a)({parentName:"tr"},{align:null}),"No"),Object(i.b)("td",Object(a.a)({parentName:"tr"},{align:null}),"Android"),Object(i.b)("td",Object(a.a)({parentName:"tr"},{align:null}),"'black'")))))}p.isMDXComponent=!0},221:function(e,t,n){"use strict";n.d(t,"a",(function(){return p})),n.d(t,"b",(function(){return O}));var a=n(0),r=n.n(a);function i(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function c(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){i(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function b(e,t){if(null==e)return{};var n,a,r=function(e,t){if(null==e)return{};var n,a,r={},i=Object.keys(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||(r[n]=e[n]);return r}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(r[n]=e[n])}return r}var l=r.a.createContext({}),u=function(e){var t=r.a.useContext(l),n=t;return e&&(n="function"==typeof e?e(t):c(c({},t),e)),n},p=function(e){var t=u(e.components);return r.a.createElement(l.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.a.createElement(r.a.Fragment,{},t)}},s=r.a.forwardRef((function(e,t){var n=e.components,a=e.mdxType,i=e.originalType,o=e.parentName,l=b(e,["components","mdxType","originalType","parentName"]),p=u(n),s=a,O=p["".concat(o,".").concat(s)]||p[s]||d[s]||i;return n?r.a.createElement(O,c(c({ref:t},l),{},{components:n})):r.a.createElement(O,c({ref:t},l))}));function O(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=n.length,o=new Array(i);o[0]=s;var c={};for(var b in t)hasOwnProperty.call(t,b)&&(c[b]=t[b]);c.originalType=e,c.mdxType="string"==typeof e?e:a,o[1]=c;for(var l=2;l<i;l++)o[l]=n[l];return r.a.createElement.apply(null,o)}return r.a.createElement.apply(null,n)}s.displayName="MDXCreateElement"},222:function(e,t,n){"use strict";var a=n(0),r=n(53);t.a=function(){return Object(a.useContext)(r.a)}},224:function(e,t,n){"use strict";n.d(t,"a",(function(){return i}));n(79);var a=n(222),r=n(225);function i(e,t){var n=void 0===t?{}:t,i=n.forcePrependBaseUrl,o=void 0!==i&&i,c=n.absolute,b=void 0!==c&&c,l=Object(a.a)().siteConfig,u=(l=void 0===l?{}:l).baseUrl,p=void 0===u?"/":u,d=l.url;if(!e)return e;if(o)return p+e;if(!Object(r.a)(e))return e;var s=p+e.replace(/^\//,"");return b?d+s:s}},225:function(e,t,n){"use strict";function a(e){return!1===/^(https?:|\/\/|mailto:|tel:)/.test(e)}n.d(t,"a",(function(){return a}))}}]);