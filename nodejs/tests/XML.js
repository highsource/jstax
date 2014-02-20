var Jstax = require('../jstax').Jstax;
module.exports =
{
	"Char": function(test) {
		var str = '\u0020\uD7FF\u0009\u000A\u000D\uE000\uFFFD';
		var input = new Jstax.Input.String(str);
		var ch = Jstax.XML.Char;
		for (var index = 0; index < str.length; index++)
		{
			test.ok(ch.matches(input));
			test.equal(str.charAt(index), ch.read(input));
		}
		test.done();
	}
};