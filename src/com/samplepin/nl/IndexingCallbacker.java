package com.samplepin.nl;

import java.util.Set;

import net.reduls.igo.Tagger;

class IndexingCallbacker implements ParserCallback {

	Tagger tagger;

	Set<String> parsed;

	public IndexingCallbacker(Tagger tagger, Set<String> parsed) {
		super();
		this.tagger = tagger;
		this.parsed = parsed;
	}

	@Override
	public void parse(CharSequence text) {
		NaturalLanguageParser.parse(this.tagger, text, this.parsed);
	}
}