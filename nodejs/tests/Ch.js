var Jstax = require('../jstax').Jstax;
module.exports =
{
	"read": function(test) {
		var input = new Jstax.Input.String('test');
		var t = new Jstax.Token.Ch('t');
		test.equal('t', t.read(input));
		test.done();
	},
	"matches": function(test) {
		var input = new Jstax.Input.String('test');
		var t = new Jstax.Token.Ch('t');
		var e = new Jstax.Token.Ch('e');
		test.equal(true, t.matches(input));
		test.equal(false, e.matches(input));
		test.done();
	}
};