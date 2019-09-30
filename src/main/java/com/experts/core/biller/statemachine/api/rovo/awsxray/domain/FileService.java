package com.experts.core.biller.statemachine.api.rovo.awsxray.domain;

import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.mongo.FileEntity;
import org.mongodb.morphia.query.FindOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService extends BaseMongoService<FileEntity> {

    public FileService() {
        super(FileEntity.class);
    }

    public long countFiles() {
        return mongoDataStore.getCount(FileEntity.class);
    }

    public List<FileEntity> listFiles(int limit, int offset) {
        return mongoDataStore.find(FileEntity.class).asList(new FindOptions().skip(offset).limit(limit));
    }
}
