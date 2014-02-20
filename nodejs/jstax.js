var Jstax = {};
// Node.js
if(typeof require === 'function'){
	module.exports.Jstax = Jstax;
}

Jstax.Util = {};

Jstax.Util.extend = function(destination, source) {
	destination = destination || {};
	if (source) {
		/*jslint forin: true */
		for ( var property in source) {
			var value = source[property];
			if (value !== undefined) {
				destination[property] = value;
			}
		}

		/**
		 * IE doesn't include the toString property when iterating over an
		 * object's properties with the for(property in object) syntax.
		 * Explicitly check if the source has its own toString property.
		 */

		/*
		 * FF/Windows < 2.0.0.13 reports "Illegal operation on WrappedNative
		 * prototype object" when calling hawOwnProperty if the source object is
		 * an instance of window.Event.
		 */

		// REWORK
		// Node.js
		var sourceIsEvt = typeof window !== 'undefined' && window !== null && typeof window.Event == "function" && source instanceof window.Event;

		if (!sourceIsEvt && source.hasOwnProperty && source.hasOwnProperty('toString')) {
			destination.toString = source.toString;
		}
	}
	return destination;
};
Jstax.Class = function() {
	var Class = function() {
		this.initialize.apply(this, arguments);
	};
	var extended = {};
	var empty = function() {
	};
	var parent, initialize, Type;
	for ( var i = 0, len = arguments.length; i < len; ++i) {
		Type = arguments[i];
		if (typeof Type == "function") {
			// make the class passed as the first argument the superclass
			if (i === 0 && len > 1) {
				initialize = Type.prototype.initialize;
				// replace the initialize method with an empty function,
				// because we do not want to create a real instance here
				Type.prototype.initialize = empty;
				// the line below makes sure that the new class has a
				// superclass
				extended = new Type();
				// restore the original initialize method
				if (initialize === undefined) {
					delete Type.prototype.initialize;
				} else {
					Type.prototype.initialize = initialize;
				}
			}
			// get the prototype of the superclass
			parent = Type.prototype;
		} else {
			// in this case we're extending with the prototype
			parent = Type;
		}
		Jstax.Util.extend(extended, parent);
	}
	Class.prototype = extended;
	return Class;
};
Jstax.Input = Jstax.Class({
	initialize: function() {}
});
Jstax.Input.String = Jstax.Class({
	str: null,
	current: 0,
	initialize: function(str)
	{
		Jstax.Input.prototype.initialize.apply(this, arguments);
        	this.str = str;
	},
	readChar: function()
	{
		// TODO check we have a char in input
		return this.str.charAt(this.current++);
	},
	peekChar: function()
	{
		// TODO check we have a char in input
		return this.str.charAt(this.current);
	}
});
Jstax.Token = Jstax.Class({
	initialize: function() {}
});
Jstax.Token.Ch = Jstax.Class({
        ch:null,
	initialize: function(ch)
	{
		// TODO check ch.length === 1
		this.ch = ch;
	},
	read: function(input)
	{
		var ch = input.readChar();
		// TODO make sure matches
		// Report an error if not
		return ch;
	},
	matches: function(input)
	{
		var ch = input.peekChar();
		return ch === this.ch;
	}
});
Jstax.Token.ChInRange = Jstax.Class({
        from:null,
        to:null,
	initialize: function(from, to)
	{
		// TODO check from.length === 1
		// TODO check to.length === 1
		this.from = from;
		this.to = to;
	},
	read: function(input)
	{
		var ch = input.readChar();
		// TODO make sure matches
		// Report an error if not
		return ch;
	},
	matches: function(input)
	{
		var ch = input.peekChar();
		return (ch >= this.from) && (ch <= this.to);
	}
});
Jstax.Token.Choice = Jstax.Class({
        tokens:null,
	initialize: function(tokens)
	{
		this.tokens = tokens;
	},
	read: function(input)
	{
		for (var index = 0; index < this.tokens.length; index++)
		{
			if (this.tokens[index].matches(input))
			{
				return this.tokens[index].read(input);
			}
		}
		// TODO Report an error: expectation was not matched.
	},
	matches: function(input)
	{
		for (var index = 0; index < this.tokens.length; index++)
		{
			if (this.tokens[index].matches(input))
			{
				return true;
			}
		}
		return false;
	}
});

Jstax.XML = {
	Char: new Jstax.Token.Choice([
		new Jstax.Token.ChInRange('\u0020', '\uD7FF'),
		new Jstax.Token.Ch('\u0009'),
		new Jstax.Token.Ch('\u000A'),
		new Jstax.Token.Ch('\u000D'),
		new Jstax.Token.ChInRange('\uE000', '\uFFFD')
		// TODO Characters above 10000 are not supported
		// new Jstax.Token.ChInRange('\u10000', '\u10FFFF'),
	])
};