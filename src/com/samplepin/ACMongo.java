package com.samplepin;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ACMongo extends Mongo implements AutoCloseable {

	public ACMongo() throws UnknownHostException, MongoException {
		super("127.0.0.1");
	}

	@Override
	public void close() {
		super.close();
	}

	public Datastore createDatastore() {
		Morphia morphia = new Morphia();
		return morphia.createDatastore(this, "sample-pin");
	}

}
