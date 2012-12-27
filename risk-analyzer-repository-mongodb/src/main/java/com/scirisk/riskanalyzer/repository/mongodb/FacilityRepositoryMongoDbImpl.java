package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class FacilityRepositoryMongoDbImpl implements FacilityRepository {

	private MongoTemplate mongoTemplate;

	public FacilityRepositoryMongoDbImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	// TODO FIXME UPDATE DOESN'T WORK!
	public Facility save(Facility facility) {
		return mongoTemplate.insert(Collection.facilities.name(), facility, entityMapper);
	}

	public Facility findOne(String facilityId) {
		return mongoTemplate.findOne(Collection.facilities.name(), facilityId, documentMapper);
	}

	public List<Facility> findAll() {
		return mongoTemplate.findAll(Collection.facilities.name(), documentMapper);
	}

	public void delete(String facilityId) {
		mongoTemplate.delete(Collection.facilities.name(), facilityId);
	}

	EntityMapper<Facility> entityMapper = new EntityMapper<Facility>() {
		@Override
		public DBObject map(Facility node) {
			BasicDBObject nodeObject = new BasicDBObject();
			nodeObject.put("kind", node.getKind().toString());
			nodeObject.put("type", node.getType().toString());
			nodeObject.put("name", node.getName());
			nodeObject.put("description", node.getDescription());

			nodeObject.put("address", node.getAddress());
			nodeObject.put("latitude", node.getLatitude());
			nodeObject.put("longitude", node.getLongitude());

			nodeObject.put("riskCategory1", node.getRiskCategory1());
			nodeObject.put("riskCategory2", node.getRiskCategory2());
			nodeObject.put("riskCategory3", node.getRiskCategory3());

			nodeObject.put("recoveryTime1", node.getRecoveryTime1());
			nodeObject.put("recoveryTime2", node.getRecoveryTime2());
			nodeObject.put("recoveryTime3", node.getRecoveryTime3());
			return nodeObject;
		}
	};

	DocumentMapper<Facility> documentMapper = new DocumentMapper<Facility>() {
		@Override
		public Facility map(DBObject document) {
			Facility facility = new Facility();
			ObjectId id = (ObjectId) document.get("_id");

			facility.setId(id.toString());
			facility.setType(Type.valueOf((String) document.get("type")));
			facility.setKind(Kind.valueOf((String) document.get("kind")));
			facility.setName((String) document.get("name"));
			facility.setDescription((String) document.get("description"));
			facility.setAddress((String) document.get("address"));
			facility.setLatitude((Double) document.get("latitude"));
			facility.setLongitude((Double) document.get("longitude"));

			facility.setRiskCategory1((Double) document.get("riskCategory1"));
			facility.setRiskCategory2((Double) document.get("riskCategory2"));
			facility.setRiskCategory3((Double) document.get("riskCategory3"));

			facility.setRecoveryTime1((Double) document.get("recoveryTime1"));
			facility.setRecoveryTime2((Double) document.get("recoveryTime2"));
			facility.setRecoveryTime3((Double) document.get("recoveryTime3"));

			return facility;
		}
	};

	@Deprecated
	boolean isBlank(String string) {
		return "".equals(string);
	}

}
