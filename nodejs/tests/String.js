var Jstax = require('../jstax').Jstax;
module.exports =
{
	"new": function(test) {
		var t = 'test';
		var input = new Jstax.Input.String(t);
		test.equal(t, input.str);
		test.done();
	}
};