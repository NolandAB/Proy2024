"use strict";(self["webpackChunkquasardesign"]=self["webpackChunkquasardesign"]||[]).push([[493],{4382:function(e,a,l){l.d(a,{I:function(){return n}});var t=l(6381);function n(e){return(0,t.A)({url:"/api/sys/clientes/table",method:"get",params:e})}},3493:function(e,a,l){l.r(a),l.d(a,{default:function(){return k}});l(9063),l(3665);var t=l(4220),n=l(9798),u=l(6732),o=l(4382),i=l(7939);const r={class:"q-pa-md"},s={class:"searchDiv row items-center q-mb-md"};var d={__name:"index",setup(e){const a=(0,u.Kh)({blurry:"",tableData:[],current:1,size:7,total:0}),l=[{name:"compania",required:!0,label:"Compañía",align:"left",field:"compania",sortable:!0},{name:"apellidos",required:!0,label:"Apellidos",align:"left",field:"apellidos",sortable:!0},{name:"nombre",required:!0,label:"Nombre",align:"left",field:"nombre",sortable:!0}],d=(0,u.KR)(!1),p=(0,u.KR)({sortBy:"nombre",descending:!1,page:1,rowsPerPage:a.size}),c=()=>{d.value=!0;const e={blurry:a.blurry,size:a.size,currentPage:p.value.page};(0,o.I)(e).then((e=>{d.value=!1,e.success?(a.tableData=e.data.records,a.total=e.data.total):(0,i.aJ)(e.msg)})).catch((()=>{d.value=!1}))},v=e=>{const{page:l,sortBy:t,descending:n}=e.pagination;p.value.sortBy=t||"",p.value.descending=n,p.value.page=l,a.current=l,c()};return(0,t.sV)((()=>{c()})),(e,u)=>{const o=(0,t.g2)("q-input"),i=(0,t.g2)("q-button"),b=(0,t.g2)("q-icon"),g=(0,t.g2)("q-table"),m=(0,t.g2)("q-pagination"),y=(0,t.g2)("q-page-container"),f=(0,t.g2)("q-layout");return(0,t.uX)(),(0,t.Wv)(f,{view:"hHh lpR fFf"},{default:(0,t.k6)((()=>[(0,t.bF)(y,null,{default:(0,t.k6)((()=>[(0,t.Lk)("div",r,[(0,t.Lk)("div",s,[(0,t.bF)(o,{class:"searchInput col-grow",modelValue:a.blurry,"onUpdate:modelValue":u[0]||(u[0]=e=>a.blurry=e),placeholder:"Ingrese nombre o apellido del cliente",clearable:"",outlined:"",onKeyup:(0,n.jR)(c,["enter"])},null,8,["modelValue"]),(0,t.bF)(i,{color:"primary",outline:"",class:"q-ml-sm custom-button",type:"primary",onClick:c},{default:(0,t.k6)((()=>u[3]||(u[3]=[(0,t.eW)(" Consulta ")]))),_:1})]),(0,t.bF)(g,{flat:"",bordered:"",ref:"tableRef",title:"Clientes",rows:a.tableData,columns:l,"row-key":"id",pagination:p.value,loading:d.value,filter:e.filter,"binary-state-sort":"","rows-per-page-options":[],onRequest:v},{"top-right":(0,t.k6)((()=>[(0,t.bF)(o,{borderless:"",dense:"",debounce:"300",modelValue:a.blurry,"onUpdate:modelValue":u[1]||(u[1]=e=>a.blurry=e),placeholder:"Buscar",onKeyup:(0,n.jR)(c,["enter"])},{append:(0,t.k6)((()=>[(0,t.bF)(b,{name:"search",onClick:c},null,8,["onClick"])])),_:1},8,["modelValue"])])),_:1},8,["rows","pagination","loading","filter"]),(0,t.bF)(m,{modelValue:p.value.page,"onUpdate:modelValue":[u[2]||(u[2]=e=>p.value.page=e),c],max:Math.ceil(a.total/a.size)},null,8,["modelValue","max"])])])),_:1})])),_:1})}}},p=l(1317),c=l(3555),v=l(6284),b=l(4985),g=l(1598),m=l(4503),y=l(4183),f=l(2110),h=l.n(f);const S=(0,p.A)(d,[["__scopeId","data-v-9a26f18c"]]);var k=S;h()(d,"components",{QLayout:c.A,QPageContainer:v.A,QInput:b.A,QTable:g.A,QIcon:m.A,QPagination:y.A})},4183:function(e,a,l){l(4645);var t=l(4220),n=l(6732),u=l(407),o=l(4985),i=l(5471),r=l(6534),s=l(9140),d=l(9012),p=l(7031);function c(e,a){return[!0,!1].includes(e)?e:a}a.A=(0,s.a0)({name:"QPagination",props:{...i.C,modelValue:{type:Number,required:!0},min:{type:[Number,String],default:1},max:{type:[Number,String],required:!0},maxPages:{type:[Number,String],default:0,validator:e=>("string"===typeof e?parseInt(e,10):e)>=0},inputStyle:[Array,String,Object],inputClass:[Array,String,Object],size:String,disable:Boolean,input:Boolean,iconPrev:String,iconNext:String,iconFirst:String,iconLast:String,toFn:Function,boundaryLinks:{type:Boolean,default:null},boundaryNumbers:{type:Boolean,default:null},directionLinks:{type:Boolean,default:null},ellipses:{type:Boolean,default:null},ripple:{type:[Boolean,Object],default:null},round:Boolean,rounded:Boolean,flat:Boolean,outline:Boolean,unelevated:Boolean,push:Boolean,glossy:Boolean,color:{type:String,default:"primary"},textColor:String,activeDesign:{type:String,default:"",values:e=>""===e||r.p.includes(e)},activeColor:String,activeTextColor:String,gutter:String,padding:{type:String,default:"3px 2px"}},emits:["update:modelValue"],setup(e,{emit:a}){const{proxy:l}=(0,t.nI)(),{$q:s}=l,v=(0,i.A)(e,s),b=(0,t.EW)((()=>parseInt(e.min,10))),g=(0,t.EW)((()=>parseInt(e.max,10))),m=(0,t.EW)((()=>parseInt(e.maxPages,10))),y=(0,t.EW)((()=>E.value+" / "+g.value)),f=(0,t.EW)((()=>c(e.boundaryLinks,e.input))),h=(0,t.EW)((()=>c(e.boundaryNumbers,!e.input))),S=(0,t.EW)((()=>c(e.directionLinks,e.input))),k=(0,t.EW)((()=>c(e.ellipses,!e.input))),V=(0,n.KR)(null),E=(0,t.EW)({get:()=>e.modelValue,set:l=>{if(l=parseInt(l,10),e.disable||isNaN(l))return;const t=(0,d.Tq)(l,b.value,g.value);e.modelValue!==t&&a("update:modelValue",t)}});(0,t.wB)((()=>`${b.value}|${g.value}`),(()=>{E.value=e.modelValue}));const q=(0,t.EW)((()=>"q-pagination row no-wrap items-center"+(!0===e.disable?" disabled":""))),x=(0,t.EW)((()=>e.gutter in r.sV?`${r.sV[e.gutter]}px`:e.gutter||null)),C=(0,t.EW)((()=>null!==x.value?`--q-pagination-gutter-parent:-${x.value};--q-pagination-gutter-child:${x.value}`:null)),W=(0,t.EW)((()=>{const a=[e.iconFirst||s.iconSet.pagination.first,e.iconPrev||s.iconSet.pagination.prev,e.iconNext||s.iconSet.pagination.next,e.iconLast||s.iconSet.pagination.last];return!0===s.lang.rtl?a.reverse():a})),B=(0,t.EW)((()=>({"aria-disabled":!0===e.disable?"true":"false",role:"navigation"}))),F=(0,t.EW)((()=>(0,r.De)(e,"flat"))),w=(0,t.EW)((()=>({[F.value]:!0,round:e.round,rounded:e.rounded,padding:e.padding,color:e.color,textColor:e.textColor,size:e.size,ripple:null===e.ripple||e.ripple}))),A=(0,t.EW)((()=>{const a={[F.value]:!1};return""!==e.activeDesign&&(a[e.activeDesign]=!0),a})),I=(0,t.EW)((()=>({...A.value,color:e.activeColor||e.color,textColor:e.activeTextColor||e.textColor}))),_=(0,t.EW)((()=>{let a=Math.max(m.value,1+(k.value?2:0)+(h.value?2:0));const l={pgFrom:b.value,pgTo:g.value,ellipsesStart:!1,ellipsesEnd:!1,boundaryStart:!1,boundaryEnd:!1,marginalStyle:{minWidth:`${Math.max(2,String(g.value).length)}em`}};return m.value&&a<g.value-b.value+1&&(a=1+2*Math.floor(a/2),l.pgFrom=Math.max(b.value,Math.min(g.value-a+1,e.modelValue-Math.floor(a/2))),l.pgTo=Math.min(g.value,l.pgFrom+a-1),h.value&&(l.boundaryStart=!0,l.pgFrom++),k.value&&l.pgFrom>b.value+(h.value?1:0)&&(l.ellipsesStart=!0,l.pgFrom++),h.value&&(l.boundaryEnd=!0,l.pgTo--),k.value&&l.pgTo<g.value-(h.value?1:0)&&(l.ellipsesEnd=!0,l.pgTo--)),l}));function N(e){E.value=e}function P(e){E.value=E.value+e}const T=(0,t.EW)((()=>{function e(){E.value=V.value,V.value=null}return{"onUpdate:modelValue":e=>{V.value=e},onKeyup:a=>{!0===(0,p.Dv)(a,13)&&e()},onBlur:e}}));function D(a,l,n){const o={"aria-label":l,"aria-current":"false",...w.value,...a};return!0===n&&Object.assign(o,{"aria-current":"true",...I.value}),void 0!==l&&(void 0!==e.toFn?o.to=e.toFn(l):o.onClick=()=>{N(l)}),(0,t.h)(u.A,o)}return Object.assign(l,{set:N,setByOffset:P}),()=>{const a=[],l=[];let n;if(!0===f.value&&(a.push(D({key:"bls",disable:e.disable||e.modelValue<=b.value,icon:W.value[0]},b.value)),l.unshift(D({key:"ble",disable:e.disable||e.modelValue>=g.value,icon:W.value[3]},g.value))),!0===S.value&&(a.push(D({key:"bdp",disable:e.disable||e.modelValue<=b.value,icon:W.value[1]},e.modelValue-1)),l.unshift(D({key:"bdn",disable:e.disable||e.modelValue>=g.value,icon:W.value[2]},e.modelValue+1))),!0!==e.input){n=[];const{pgFrom:t,pgTo:u,marginalStyle:o}=_.value;if(!0===_.value.boundaryStart){const l=b.value===e.modelValue;a.push(D({key:"bns",style:o,disable:e.disable,label:b.value},b.value,l))}if(!0===_.value.boundaryEnd){const a=g.value===e.modelValue;l.unshift(D({key:"bne",style:o,disable:e.disable,label:g.value},g.value,a))}!0===_.value.ellipsesStart&&a.push(D({key:"bes",style:o,disable:e.disable,label:"…",ripple:!1},t-1)),!0===_.value.ellipsesEnd&&l.unshift(D({key:"bee",style:o,disable:e.disable,label:"…",ripple:!1},u+1));for(let a=t;a<=u;a++)n.push(D({key:`bpg${a}`,style:o,disable:e.disable,label:a},a,a===e.modelValue))}return(0,t.h)("div",{class:q.value,...B.value},[(0,t.h)("div",{class:"q-pagination__content row no-wrap items-center",style:C.value},[...a,!0===e.input?(0,t.h)(o.A,{class:"inline",style:{width:y.value.length/1.5+"em"},type:"number",dense:!0,value:V.value,disable:e.disable,dark:v.value,borderless:!0,inputClass:e.inputClass,inputStyle:e.inputStyle,placeholder:y.value,min:b.value,max:g.value,...T.value}):(0,t.h)("div",{class:"q-pagination__middle row justify-center"},n),...l])])}}})}}]);
//# sourceMappingURL=493.19d65e8c.js.map