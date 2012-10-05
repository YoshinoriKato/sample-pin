package com.samplepin.nl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import net.reduls.igo.Tagger;

class TaggingCallbacker implements ParserCallback {

	Tagger tagger;
	Map<String, AtomicInteger> counts;

	public TaggingCallbacker(Tagger tagger, Map<String, AtomicInteger> counts) {
		super();
		this.tagger = tagger;
		this.counts = counts;
	}

	@Override
	public void parse(CharSequence text) {
		NaturalLanguageParser.parse(this.tagger, text, this.counts);
	}
}