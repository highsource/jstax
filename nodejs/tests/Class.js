var Jstax = require('../jstax').Jstax;
module.exports =
{
	"greet": function(test) {
		var Hello = Jstax.Class(
			{
				name : null,
				initialize: function( name ) { this.name = name },
				greet : function () { return 'Hello, ' + this.name + '!' }
				
				
			});
		test.equal("Hello, World!", new Hello('World').greet());
		test.done();
	}
};