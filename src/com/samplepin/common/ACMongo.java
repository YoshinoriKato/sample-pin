package com.samplepin.common;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ACMongo extends Mongo implements AutoCloseable {

	String	hostName	= "";

	String	dbName		= "";

	public ACMongo() throws UnknownHostException, MongoException {
		this("127.0.0.1", "sample-pin");
	}

	public ACMongo(String hostName, String dbName) throws UnknownHostException,
			MongoException {
		super(hostName);
		this.hostName = hostName;
		this.dbName = dbName;
	}

	@Override
	public void close() {
		super.close();
	}

	public Datastore createDatastore() {
		Morphia morphia = new Morphia();
		return morphia.createDatastore(this, this.dbName);
	}

	public <T> Query<T> createQuery(Class<T> clazz) {
		Datastore datastore = createDatastore();
		Query<T> query = datastore.createQuery(clazz);
		return query;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getHostName() {
		return this.hostName;
	}

	public <T> void save(Iterable<T> iterable) {
		Datastore datastore = createDatastore();
		datastore.save(iterable);
	}

	public <T> void save(T t) {
		Datastore datastore = createDatastore();
		datastore.save(t);
	}

}
