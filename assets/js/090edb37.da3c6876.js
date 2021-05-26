(window.webpackJsonp=window.webpackJsonp||[]).push([[16],{468:function(e,t,n){"use strict";n.d(t,"a",(function(){return d})),n.d(t,"b",(function(){return m}));var a=n(0),r=n.n(a);function i(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function l(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){i(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,a,r=function(e,t){if(null==e)return{};var n,a,r={},i=Object.keys(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||(r[n]=e[n]);return r}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(r[n]=e[n])}return r}var b=r.a.createContext({}),c=function(e){var t=r.a.useContext(b),n=t;return e&&(n="function"==typeof e?e(t):l(l({},t),e)),n},d=function(e){var t=c(e.components);return r.a.createElement(b.Provider,{value:t},e.children)},p={inlineCode:"code",wrapper:function(e){var t=e.children;return r.a.createElement(r.a.Fragment,{},t)}},u=r.a.forwardRef((function(e,t){var n=e.components,a=e.mdxType,i=e.originalType,o=e.parentName,b=s(e,["components","mdxType","originalType","parentName"]),d=c(n),u=a,m=d["".concat(o,".").concat(u)]||d[u]||p[u]||i;return n?r.a.createElement(m,l(l({ref:t},b),{},{components:n})):r.a.createElement(m,l({ref:t},b))}));function m(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=n.length,o=new Array(i);o[0]=u;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l.mdxType="string"==typeof e?e:a,o[1]=l;for(var b=2;b<i;b++)o[b]=n[b];return r.a.createElement.apply(null,o)}return r.a.createElement.apply(null,n)}u.displayName="MDXCreateElement"},80:function(e,t,n){"use strict";n.r(t),n.d(t,"frontMatter",(function(){return o})),n.d(t,"metadata",(function(){return l})),n.d(t,"toc",(function(){return s})),n.d(t,"default",(function(){return c}));var a=n(3),r=n(7),i=(n(0),n(468)),o={id:"meta-contributing",title:"Contributing",sidebar_label:"Contributing"},l={unversionedId:"docs/meta-contributing",id:"version-7.15.0/docs/meta-contributing",isDocsHomePage:!1,title:"Contributing",description:"Thank you for showing interest in contributing to React-Native-Navigation! This project is developed and maintained by Wix in collaboration with the community.",source:"@site/versioned_docs/version-7.15.0/docs/meta-contributing.mdx",slug:"/docs/meta-contributing",permalink:"/react-native-navigation/docs/meta-contributing",editUrl:"https://github.com/wix/react-native-navigation/edit/master/website/versioned_docs/version-7.15.0/docs/meta-contributing.mdx",version:"7.15.0",sidebar_label:"Contributing",sidebar:"version-7.15.0/docs",previous:{title:"React Context API",permalink:"/react-native-navigation/docs/third-party-react-context"}},s=[{value:"Stack Overflow",id:"stack-overflow",children:[]},{value:"Discord",id:"discord",children:[]},{value:"Contributing to React-Native-Navigation",id:"contributing-to-react-native-navigation",children:[{value:"iOS",id:"ios",children:[]}]},{value:"GitHub Issues",id:"github-issues",children:[{value:"Open an issue",id:"open-an-issue",children:[]},{value:"Respond to issues",id:"respond-to-issues",children:[]},{value:"Provide reproductions",id:"provide-reproductions",children:[]}]},{value:"Submitting PRs",id:"submitting-prs",children:[]},{value:"Workflow",id:"workflow",children:[{value:"Tests and the Playground app",id:"tests-and-the-playground-app",children:[]},{value:"Enable linter",id:"enable-linter",children:[]},{value:"Playground app Folder Structure",id:"playground-app-folder-structure",children:[]},{value:"Scripts",id:"scripts",children:[]}]}],b={toc:s};function c(e){var t=e.components,n=Object(r.a)(e,["components"]);return Object(i.b)("wrapper",Object(a.a)({},b,n,{components:t,mdxType:"MDXLayout"}),Object(i.b)("p",null,"Thank you for showing interest in contributing to React-Native-Navigation! This project is developed and maintained by Wix in collaboration with the community."),Object(i.b)("p",null,"There are various ways in which you can contribute to this library, not all require knowledge or expertise in native development. Listed below is all you need to get started with your first contribution."),Object(i.b)("h2",{id:"stack-overflow"},"Stack Overflow"),Object(i.b)("p",null,"Stack Overflow is used by developers to find answers and troubleshoot code. Users are encouraged to post questions on SO and tag them with the ",Object(i.b)("a",{parentName:"p",href:"https://stackoverflow.com/questions/tagged/wix-react-native-navigation"},"wix-react-native-navigation")," tag. Answering issues on SO is very helpful and beneficial to the community, only this time, there's a personal angle - you can boost your SO ranking and rack up points quickly."),Object(i.b)("h2",{id:"discord"},"Discord"),Object(i.b)("p",null,"Another popular communication channel is our ",Object(i.b)("a",{parentName:"p",href:"https://discord.gg/DhkZjq2"},"Discord channel")," where users post questions and consult with each other. You're welcome to join the discussions and answer questions. This is also a great place to meet other community members and project maintainers."),Object(i.b)("h2",{id:"contributing-to-react-native-navigation"},"Contributing to React-Native-Navigation"),Object(i.b)("h3",{id:"ios"},"iOS"),Object(i.b)("p",null,"There are 2 ways to test your contribution to this project on iOS:"),Object(i.b)("ol",null,Object(i.b)("li",{parentName:"ol"},"You can contribute and test your changes using the playground provided.")),Object(i.b)("ol",{start:2},Object(i.b)("li",{parentName:"ol"},"You can install React-Native-Navigation in a new or existing project and set your project to use your local clone of React-Native-Navigation in the podfile.\nThe following steps are required in order to make the project work with your local environment:")),Object(i.b)("h4",{id:"21"},"2.1"),Object(i.b)("p",null,"Install React-Native-Navigation as you'll usually do in your project, using ",Object(i.b)("inlineCode",{parentName:"p"},"npm install --save react-native-navigation"),"."),Object(i.b)("h4",{id:"22"},"2.2"),Object(i.b)("p",null,"Add the following configuration to the ",Object(i.b)("inlineCode",{parentName:"p"},"react-native.config.js")," file, at the root of your project."),Object(i.b)("pre",null,Object(i.b)("code",{parentName:"pre",className:"language-javascript"},"module.exports = {\n    project: {\n        ios: {},\n        android: {},\n    },\n    dependencies: {\n        'react-native-navigation': {\n            platforms: {\n                ios: null,\n                android: null\n            },\n        },\n    },\n};\n")),Object(i.b)("h4",{id:"23"},"2.3"),Object(i.b)("p",null,"Clone the project using ",Object(i.b)("inlineCode",{parentName:"p"},"git clone https://github.com/wix/react-native-navigation")," at your desired location."),Object(i.b)("h4",{id:"24"},"2.4"),Object(i.b)("p",null,"Add the following line in the Podfile:"),Object(i.b)("pre",null,Object(i.b)("code",{parentName:"pre",className:"language-ruby"},"pod 'ReactNativeNavigation', :path => '/path/to/cloned/react-native-navigation'\n")),Object(i.b)("p",null,"You're done. Happy coding!"),Object(i.b)("h2",{id:"github-issues"},"GitHub Issues"),Object(i.b)("h3",{id:"open-an-issue"},"Open an issue"),Object(i.b)("p",null,"Found a bug? Missing a feature? Go ahead and open an issue! Make sure to add all details mentioned in the issue template. If you're interested in suggesting a big change, please speak to one of the admins on ",Object(i.b)("a",{parentName:"p",href:"#discord"},"Discord")," to coordinate your effort."),Object(i.b)("h3",{id:"respond-to-issues"},"Respond to issues"),Object(i.b)("p",null,"Although the issue tracker is used solely for bug reports, issues are frequently opened for questions or to request assistance. As the project grows in popularity, more issues remain unanswered for long periods of time."),Object(i.b)("p",null,"Some issues can be trivial and easy to pinpoint - a missing import statement or apostrophe, wrong layout structure, etc. If you're an experienced user, helping out newcomers can be quite satisfying and allows maintainers to focus on features and bug fixes."),Object(i.b)("p",null,"Some issues are tagged as ",Object(i.b)("a",{parentName:"p",href:"https://github.com/wix/react-native-navigation/labels/user%3A%20looking%20for%20contributors"},'"looking for contributors"'),". These issues have been recognized by the team, but aren't prioritized by Wix developers due to lack of time or for some other reason. We leave these issues to our community and you're more than welcome to take a crack at them. If you'd like to submit a PR, see ",Object(i.b)("a",{parentName:"p",href:"#running-the-project"},"these instructions")," on how to run the project locally."),Object(i.b)("h3",{id:"provide-reproductions"},"Provide reproductions"),Object(i.b)("p",null,"Reproducing bugs takes time. Lots of time. Usually that's because an issue is lacking important information, which then causes lots of back and forth communication between maintainers and users. Help us address more bugs and issues by providing reproductions."),Object(i.b)("p",null,"Providing reproductions improves the chances of an issue being prioritized by maintainers!"),Object(i.b)("p",null,"If an issue is reproduced with a specific combination of options, posting these options will usually suffice. If a specific layout structure is involved or specific actions need to be performed in a certain order - then we ask that you fork the project and push a branch with a reproduction to the Playground app."),Object(i.b)("p",null,"Check out the ",Object(i.b)("a",{parentName:"p",href:"https://github.com/wix/react-native-navigation/labels/user%3A%20requires%20reproduction"},"list of issues requiring reproductions"),"."),Object(i.b)("h2",{id:"submitting-prs"},"Submitting PRs"),Object(i.b)("p",null,"So you've fixed a bug or added a feature and you're ready to submit a pull request \ud83c\udf89\ud83c\udf8a Make sure the title is clear and describes the reason for the PR. Please include any information you can which will help us understand the reasons for the PR, what it fixes and what it changes. Please include before/after pictures/gifs when appropriate."),Object(i.b)("h2",{id:"workflow"},"Workflow"),Object(i.b)("p",null,"This project is driven by tests. Before implementing any feature or fixing any bug, a failing test (e2e or unit or both) should be added, depending on the environment of where the fix should be implemented. For example, for an API change, a failing e2e should be written. For a small bug fix in Android, for example, a unit test in Android should be added."),Object(i.b)("p",null,"This will ensure good quality throughout the life of the project and will help avoid unexpected breakages."),Object(i.b)("p",null,"No PR will be accepted without adequate test coverage."),Object(i.b)("p",null,"If you need help running the project, have a look at the ",Object(i.b)("a",{parentName:"p",href:"playground-app"},"Playground app")," section. You can run the tests using the scripts below."),Object(i.b)("h3",{id:"tests-and-the-playground-app"},"Tests and the Playground app"),Object(i.b)("p",null,"Besides an overview of basic React Native Navigation functionality, the ",Object(i.b)("a",{parentName:"p",href:"playground-app"},"Playground app")," can (and rather should) be used for e2e tests and reproductions. If your change requires an e2e, add it to the playground app, to the appropriate screen. Again, quick setup instructions available in ",Object(i.b)("a",{parentName:"p",href:"playground-app"},"Playground app")," section of these docs."),Object(i.b)("div",{className:"admonition admonition-tip alert alert--success"},Object(i.b)("div",{parentName:"div",className:"admonition-heading"},Object(i.b)("h5",{parentName:"div"},Object(i.b)("span",{parentName:"h5",className:"admonition-icon"},Object(i.b)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"12",height:"16",viewBox:"0 0 12 16"},Object(i.b)("path",{parentName:"svg",fillRule:"evenodd",d:"M6.5 0C3.48 0 1 2.19 1 5c0 .92.55 2.25 1 3 1.34 2.25 1.78 2.78 2 4v1h5v-1c.22-1.22.66-1.75 2-4 .45-.75 1-2.08 1-3 0-2.81-2.48-5-5.5-5zm3.64 7.48c-.25.44-.47.8-.67 1.11-.86 1.41-1.25 2.06-1.45 3.23-.02.05-.02.11-.02.17H5c0-.06 0-.13-.02-.17-.2-1.17-.59-1.83-1.45-3.23-.2-.31-.42-.67-.67-1.11C2.44 6.78 2 5.65 2 5c0-2.2 2.02-4 4.5-4 1.22 0 2.36.42 3.22 1.19C10.55 2.94 11 3.94 11 5c0 .66-.44 1.78-.86 2.48zM4 14h5c-.23 1.14-1.3 2-2.5 2s-2.27-.86-2.5-2z"}))),"tip")),Object(i.b)("div",{parentName:"div",className:"admonition-content"},Object(i.b)("p",{parentName:"div"},"If a screen contains too many buttons, e2e tests might fail if the button is positioned out of screen bounds because Detox matchers detect it's invisible."))),Object(i.b)("h3",{id:"enable-linter"},"Enable linter"),Object(i.b)("p",null,"The project uses ",Object(i.b)("a",{parentName:"p",href:"https://eslint.org/"},"ESLint")," with ",Object(i.b)("a",{parentName:"p",href:"https://prettier.io/"},"Prettier")," to ensure code style consistency across the codebase. Make sure to install these plugins in your IDE."),Object(i.b)("p",null,"A pre-commit hook will verify that the linter passes when committing."),Object(i.b)("h3",{id:"playground-app-folder-structure"},"Playground app Folder Structure"),Object(i.b)("table",null,Object(i.b)("thead",{parentName:"table"},Object(i.b)("tr",{parentName:"thead"},Object(i.b)("th",{parentName:"tr",align:null},"Folder"),Object(i.b)("th",{parentName:"tr",align:null},"Description"))),Object(i.b)("tbody",{parentName:"table"},Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"lib")),Object(i.b)("td",{parentName:"tr",align:null},"The project itself composed of:")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"lib/android")),Object(i.b)("td",{parentName:"tr",align:null},"android sources and unit tests")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"lib/ios")),Object(i.b)("td",{parentName:"tr",align:null},"iOS sources and unit tests")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"lib/src")),Object(i.b)("td",{parentName:"tr",align:null},"TypeScript sources and unit tests")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"lib/dist")),Object(i.b)("td",{parentName:"tr",align:null},"compiled javascript sources and unit tests")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"lib/dist/index.js")),Object(i.b)("td",{parentName:"tr",align:null},"the entry point for ",Object(i.b)("inlineCode",{parentName:"td"},"import Navigation from 'react-native-navigation'"))),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"e2e")),Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("a",{parentName:"td",href:"https://github.com/wix/detox"},"detox")," e2e tests on both Android and iOS")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"playground")),Object(i.b)("td",{parentName:"tr",align:null},"The end-user project all e2e tests run against. Contains its own ",Object(i.b)("inlineCode",{parentName:"td"},"src"),", ",Object(i.b)("inlineCode",{parentName:"td"},"android")," and ",Object(i.b)("inlineCode",{parentName:"td"},"ios"),". Does not have its own package.json, depends on the local ",Object(i.b)("inlineCode",{parentName:"td"},"<root>/lib")," for faster local development (no need to ",Object(i.b)("inlineCode",{parentName:"td"},"npm install")," locally).")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"integration")),Object(i.b)("td",{parentName:"tr",align:null},"misc javascript integration tests, proving integration with other libraries like redux")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"scripts")),Object(i.b)("td",{parentName:"tr",align:null},"all scripts")))),Object(i.b)("h3",{id:"scripts"},"Scripts"),Object(i.b)("table",null,Object(i.b)("thead",{parentName:"table"},Object(i.b)("tr",{parentName:"thead"},Object(i.b)("th",{parentName:"tr",align:null},"Command"),Object(i.b)("th",{parentName:"tr",align:null},"Description"))),Object(i.b)("tbody",{parentName:"table"},Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm install")),Object(i.b)("td",{parentName:"tr",align:null},"installs dependencies")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run build")),Object(i.b)("td",{parentName:"tr",align:null},"compiles TypeScript sources ",Object(i.b)("inlineCode",{parentName:"td"},"./lib/src")," into javascript ",Object(i.b)("inlineCode",{parentName:"td"},"./lib/dist"))),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run clean")),Object(i.b)("td",{parentName:"tr",align:null},"cleans all build directories, stops packager, fixes flakiness by removing watchman cache, etc.")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run start")),Object(i.b)("td",{parentName:"tr",align:null},"starts the react-native packager for local debugging")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run xcode")),Object(i.b)("td",{parentName:"tr",align:null},"for convenience, opens xcode in this project")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run install-android")),Object(i.b)("td",{parentName:"tr",align:null},"builds playground debug/release version and installs on running android devices/emulators. ",Object(i.b)("br",null)," ",Object(i.b)("strong",{parentName:"td"},"Options:")," ",Object(i.b)("inlineCode",{parentName:"td"},"-- --release"))),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run uninstall-android")),Object(i.b)("td",{parentName:"tr",align:null},"uninstalls playground from running android devices/simulators")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run test-js")),Object(i.b)("td",{parentName:"tr",align:null},"runs javascript tests and coverage report")),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run test-unit-ios")),Object(i.b)("td",{parentName:"tr",align:null},"runs ios unit tests in debug/release ",Object(i.b)("br",null)," ",Object(i.b)("strong",{parentName:"td"},"Options:")," ",Object(i.b)("inlineCode",{parentName:"td"},"-- --release"))),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run test-unit-android")),Object(i.b)("td",{parentName:"tr",align:null},"runs android unit tests in debug/release ",Object(i.b)("br",null)," ",Object(i.b)("strong",{parentName:"td"},"Options:")," ",Object(i.b)("inlineCode",{parentName:"td"},"-- --release"))),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run test-e2e-ios")),Object(i.b)("td",{parentName:"tr",align:null},"runs the ios e2e tests using ",Object(i.b)("a",{parentName:"td",href:"https://github.com/wix/detox"},"detox")," in debug/release ",Object(i.b)("br",null)," ",Object(i.b)("strong",{parentName:"td"},"Options:")," ",Object(i.b)("inlineCode",{parentName:"td"},"-- --release"))),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run test-e2e-android")),Object(i.b)("td",{parentName:"tr",align:null},"runs the android e2e tests using ",Object(i.b)("a",{parentName:"td",href:"https://github.com/wix/detox"},"detox")," in debug/release ",Object(i.b)("br",null)," ",Object(i.b)("strong",{parentName:"td"},"Options:")," ",Object(i.b)("inlineCode",{parentName:"td"},"-- --release"))),Object(i.b)("tr",{parentName:"tbody"},Object(i.b)("td",{parentName:"tr",align:null},Object(i.b)("inlineCode",{parentName:"td"},"npm run test-all")),Object(i.b)("td",{parentName:"tr",align:null},"runs all tests in parallel")))))}c.isMDXComponent=!0}}]);