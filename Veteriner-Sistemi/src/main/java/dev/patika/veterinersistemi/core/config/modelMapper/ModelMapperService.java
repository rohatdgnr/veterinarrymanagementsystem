package dev.patika.veterinersistemi.core.config.modelMapper;

;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapperService implements IModelMapperService {
    private final ModelMapper modelMapper;



    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }


    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(false).setMatchingStrategy(MatchingStrategies.STRICT);
        return this.modelMapper;
    }
}