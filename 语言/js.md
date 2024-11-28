###  基础
* 第`==`比较会自动转换数据类型再比较；`===`比较不会自动转换数据类型，如果数据类型不一致，返回`false`，如果一致，再比较。由于JavaScript这个设计缺陷，不要使用`==`比较，始终坚持使用`===`比较。
* `NaN`这个特殊的Number与所有其他值都不相等，包括它自己。唯一能判断`NaN`的方法是通过`isNaN()`函数。
* 用`null`表示一个空的值，而`undefined`表示值未定义。大多数情况下都应该用`null`，`undefined`仅仅在判断函数参数是否传递的情况下有用。
* 如果一个变量没有通过`var`申明就被使用，那么该变量就自动被申明为全局变量。使用`var`申明的变量则不是全局变量，它的范围被限制在该变量被申明的函数体内。
* 在strict模式下运行的JavaScript代码，强制通过`var`申明变量，未使用`var`申明变量就使用的，将导致运行错误。在JavaScript代码的第一行写上：`'use strict';`开启strict模式。
* 多行字符串的表示方法，用反引号`xxx `表示。
* 模板字符串，反引号内使用`${varNmae}`表示引用变量。
* 字符串支持数组下标访问，字符串是不可变的，对单个字符赋值无效。
* 直接给`Array`的`length`赋一个新的值会导致`Array`大小的变化，造成扩容或者截断。如果通过索引赋值时，索引超过了范围，同样会引起`Array`大小的变化。
* `slice()`截取`Array`的部分元素，浅copy返回一个新的`Array`。
* `push()`向`Array`的末尾添加若干元素，`pop()`则把`Array`的最后一个元素删除掉。使用`unshift()`方法往`Array`的头部添加若干元素，`shift()`方法则把`Array`的第一个元素删掉
* `splice()`方法可以从指定的索引开始删除若干元素，然后再从该位置添加若干元素。`arr.splice(start, cnt, var1, var2);`
* JavaScript把`null`、`undefined`、`0`、`NaN`和空字符串`''`视为`false`，其他值一概视为`true`
* Map与Set
```js
var m = new Map(); // 空Map
var m = new Map([['Michael', 95], ['Bob', 75], ['Tracy', 85]]);// 带初始值的map
m.set('Adam', 67); // 添加新的key-value
m.get('Adam'); // 67

var s1 = new Set(); // 空Set
var s2 = new Set([1, 2, 3]); // 含1, 2, 3
s.add(4);
```
* 遍历集合
```js
for (var x of vs) { 
    // array、set返回值
    // map返回[k,v]数组
}
vs.forEach(function (element, index, array) {
    // element: 指向当前元素的值
    // index: 指向当前索引,array为下标，map为key，set为value
    // array: 指向Array对象本身
    // 由于JavaScript的函数调用不要求参数必须一致，因此可以忽略部分参数
    // vs.forEach(function (element) {})
});
```
### 函数
* JavaScript允许传入任意个参数而不影响调用，传入的参数与定义的参数不匹配也没有问题，多传入参数将不会被用，缺少的形参为`undefined`
```js
function abs(x) {}
var abs = function (x) {};
```
* 利用`arguments`可以在方法内获得调用者传入的所有实参。`arguments`类似`Array`但它不是一个`Array`。
* 方法形参使用`...rest`实现可变参数传入。使用数组方式访问`rest`。
* JavaScript的函数会先扫描整个函数体的语句，把所有申明的变量的声明提到顶部，但不会提升变量的赋值。所以要在函数内部首先申明所有变量，防止`undefined`出现。
* JavaScript默认有一个全局对象`window`，全局作用域的变量实际上被绑定到`window`的一个属性。
* 全局变量会绑定到`window`上，不同的JavaScript文件如果使用了相同的全局变量，或者定义了相同名字的顶层函数，都会造成命名冲突。减少冲突的一个方法是把所有变量和函数全部绑定到一个全局变量中，通过命名空间划分。
```js
// 唯一的全局变量MYAPP:
var MYAPP = {};
// 其他变量:
MYAPP.name = 'myapp';
MYAPP.version = 1.0;
// 其他函数:
MYAPP.foo = function () {
    return 'foo';
};
```
* 由于JavaScript的变量作用域实际上是函数内部，在`for`循环等语句块中是无法定义具有局部作用域的变量。
```js
function foo() {
    for (var i=0; i<100; i++) {
        //
    }
    i += 100; // 仍然可以引用变量i
}
```
用`let`替代`var`可以申明一个块级作用域的变量：
```js
function foo() {
    var sum = 0;
    for (let i=0; i<100; i++) {
        sum += i;
    }
    // SyntaxError:
    i += 1;
}
```
* 使用解构赋值，直接对多个变量同时赋值：`var [x, y, z] = ['hello', 'JavaScript', 'ES6'];`如果数组本身还有嵌套，也可以进行解构赋值，注意两侧嵌套层次和位置要保持一致：`let [x, [y, z]] = ['hello', ['JavaScript', 'ES6']];`
* 从一个对象中取出若干属性，也可以使用解构赋值，便于快速获取对象的指定属性，同样可以直接对嵌套的对象属性进行赋值
```js
var person = {
    name: '小明',
    age: 20,
    gender: 'male',
    passport: 'G-12345678',
    school: 'No.4 middle school',
    address: {
        city: 'Beijing',
        street: 'No.1 Road',
        zipcode: '100001'
    }
};
var {name, address: {city, zip}} = person;// 变量名要与字段名对应，否则变量将被赋值为`undefined`
let {name, passport:id} = person;//变量id获取password字段值
var {name, single=true} = person;//不存在对应字段，使用默认值
```
* 在机构体中定义方法
```js
var xiaoming = {
    name: '小明',
    birth: 1990,
    age: function () {
        var y = new Date().getFullYear();
        return y - this.birth;
    }
};
```
* 在结构体外绑定方法。方法通过this访问结构体成员
```js
function getAge() {
    var y = new Date().getFullYear();
    return y - this.birth;
}

var xiaoming = {
    name: '小明',
    birth: 1990,
    age: getAge
};

xiaoming.age(); // 此时this指向调用者也就是xiaoming
getAge.apply(xiaoming, []); // 25, 手动设置this指向xiaoming, 方法参数为空。apply()把参数打包成Array再传入；call()把参数按顺序传入。对普通函数调用，我们通常把this绑定为null
```
* `vs.map(fc)`：接收函数作为参数，本质使用`for of`遍历集合对象，传入`fc`的参数为`(element, index, array)`，需要与`fc`形参匹配，多余参数被丢弃。如果不匹配需要手动转换。
```js
function pow(x) {
    return x * x;
}
var arr = [1, 2, 3];
var results = arr.map(pow);// 只用了element参数，index, array被丢弃

results = arr.map(function (v, i, vs) {
    return v ** 2;
});// 手动匹配参数
```

* `vs.reduce(fc)`：接收函数作为参数，`fc`需要两个形参，把结果继续和序列的下一个元素做累积计算。
```js
var arr = [1, 3, 5, 7, 9];
arr.reduce(function (x, y) {
    return x + y;
}); // 25
```
* `filter`：接收函数作为参数，本质使用`for of`遍历集合对象，传入`fc`的参数为`(element, index, array)`，需要与`fc`形参匹配，多余参数被丢弃，`fc`返回布尔值。如果不匹配需要手动转换。
```js
arr.filter(function (x) {
    return x % 2 !== 0;
});
```
* 排序：`Array`的`sort()`方法默认把所有元素先转换为String再排序，需要一个比较函数来实现自定义的排序。
* 数组判断查找
```js
arr.find(fc)//查找符合条件的第一个元素，找到了返回，否则返回`undefined`。
arr.every(fc)//判断数组的所有元素是否满足测试条件。
arr.find(fc)//查找符合条件的第一个元素的值，如果没有找到，返回`undefined`。
arr.findIndex(fc)//查找符合条件的第一个元素的下标，如果没有找到，返回`-1`。
```
* 闭包：返回值为函数，内部函数可以引用外部函数的参数和局部变量，相关参数和变量都保存在返回的函数中。
* 每次调用都会返回一个新的函数，即使传入相同的参数。
* 返回的函数并没有立刻执行，而是直到调用了`f()`才执行。内部引用变量只在执行时才去读取，导致创建闭包时的变量和执行闭包时的变量值不一致。所以返回函数不要引用任何循环变量，或者后续会发生变化的变量。
```js
function count() {
    var arr = [];
    for (var i=1; i<=3; i++) {
        arr.push(function () {
            return i * i;
        });
    }
    return arr;
}
var results = count();
var f1 = results[0];
var f2 = results[1];
var f3 = results[2];
f1(); // 16
f2(); // 16
f3(); // 16
```
* 借助闭包，同样可以封装一个私有变量。
```js
function create_counter(initial) {
    var x = initial || 0;
    return function () {
        x += 1;
        return x;
    }
}

var c1 = create_counter();
console.log(c1())//1
console.log(c1())//2
console.log(c1())//3
```
* 箭头函数相当于匿名函数，并且简化了函数定义，定义与lambda表达式相同。
```js
function (x) {
    return x * x;
}
x => x * x
```
* generator可以返回多次。generator由`function*`定义，并且除了`return`语句，还可以用`yield`返回多次。把同步的代码以异步的方式执行。
```js
function* foo(x) {
    yield x + 1;
    yield x + 2;
    return x + 3;
}
```
* 可以通过不断地调用generator对象的`next()`方法，获得返回值。`next()`方法会执行generator的代码，然后，每次遇到`yield x;`就返回一个对象`{value: x, done: true/false}`，然后“暂停”。返回的`value`就是`yield`的返回值，`done`表示这个generator是否已经执行结束了。如果`done`为`true`，则`value`就是`return`的返回值。
```js
var f = foo(5);
console.log(f.next()) // {value: 6, done: false}
console.log(f.next()) // {value: 7, done: false}
console.log(f.next()) // {value: 8, done: true}
```
* 用`for ... of`循环迭代generator对象，这种方式不需要我们自己判断`done`，当时只返回`yield`的值，如果`return`有返回值将不会被调用
```js
for (let v of foo(5)) {
    console.log(v); // 6 7
}
```
### 对象
* JavaScript不区分类和实例的概念，而是通过原型（prototype）来实现面向对象编程。所有对象都是实例，所谓继承关系不过是把一个对象的原型指向另一个对象而已。
* JavaScript对每个创建的对象都会设置一个原型，指向它的原型对象。当用`obj.xxx`访问一个对象的属性时，JavaScript引擎先在当前对象上查找该属性，如果没有找到，就到其原型对象上找，如果还没有找到，就一直上溯到`Object.prototype`对象，最后，如果还没有找到，就只能返回`undefined`。
````
arr ----> Array.prototype ----> Object.prototype ----> null
````
* 用一种构造函数的方法来创建对象，构造函数首字母应当大写，编写方式与Java相似。
```js
'use strict';
function Student(name) {
    this.name = name;
    this.hello = function () {
        console.log('Hello, ' + this.name + '!');
    }
    // this指向新创建的对象，并默认返回this
}
// xiaoming ----> Student.prototype ----> Object.prototype ----> null
var xiaoming = new Student('小明');
console.log(xiaoming.name);; // '小明'
xiaoming.hello(); // Hello, 小明!
```
![[Pasted image 20230622135753.png]]
每个对象有独立的字段和方法。`a.hello === b.hello;//false`
* 为结构体添加方法，对象的`hello`函数实际上只需要共享同一个函数。`a.hello === b.hello;//true`
```js
Student.prototype.hello = function () {
    console.log('hello, ' + this.name + '!');
};
xiaoming.hello();// hello, 小明!
```
![[Pasted image 20230622135944.png]]
* `class`类型定义
```js
class Student {
    constructor(name) {
        this.name = name;
    }
    hello() {
        alert('Hello, ' + this.name + '!');
    }
}
var xiaoming = new Student('小明');
xiaoming.hello();
```
* `class`类型继承
```js
class PrimaryStudent extends Student {
    constructor(name, grade) {
        super(name); // 用super调用父类的构造方法
        this.grade = grade;
    }
    myGrade() {
        alert('I am at grade ' + this.grade);
    }
}
```
### NodeJs
* 包管理：使用`module.exports`对外暴露，模块名为文件名。使用`require`引入模块
```js
module.exports={Hello,Greet};// hello.js
m = require('./hello');
m.Greet('name');
```