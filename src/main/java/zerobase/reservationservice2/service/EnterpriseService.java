package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.exception.EnterpriseException;
import zerobase.reservationservice2.exception.ErrorCode;
import zerobase.reservationservice2.model.RegEnterprise;
import zerobase.reservationservice2.repository.EnterpriseRepository;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;



    public EnterpriseEntity register(RegEnterprise.regEnterprise request, String userId) {
        validateRegisterEnterprise(request);

        var result = enterpriseRepository.save(request.toEntity(userId));

        return result;
    }

    private void validateRegisterEnterprise(RegEnterprise.regEnterprise request) {
        if (enterpriseRepository.existsByEnterpriseName(request.getEnterpriseName())
        && enterpriseRepository.existsByEnterpriseAddress(request.getEnterpriseAddress()))
            throw new EnterpriseException(ErrorCode.ALREADY_EXISTS_ENTERPRISE);
    }
}
