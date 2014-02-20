var Jstax = require('../jstax').Jstax;
module.exports =
{
	"read": function(test) {
		var input = new Jstax.Input.String('test');
		var t = new Jstax.Token.ChInRange('\u0020', '\u0D7FF');
		test.equal('t', t.read(input));
		test.done();
	},
	"matches": function(test) {
		var input = new Jstax.Input.String('test');
		var az = new Jstax.Token.ChInRange('a', 'z');
		var _09 = new Jstax.Token.ChInRange('0', '9');
		test.equal(true, az.matches(input));
		test.equal(false, _09.matches(input));
		test.done();
	}
};