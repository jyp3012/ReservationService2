package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.exception.EnterpriseException;
import zerobase.reservationservice2.exception.ErrorCode;
import zerobase.reservationservice2.model.RegEnterprise;
import zerobase.reservationservice2.repository.EnterpriseRepository;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    private final long NOT_EXISTS_USER = 0L;

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

    public boolean unRegister(RegEnterprise.unRegEnterprise request, String userId) {
       return validateUnRegisterEnterprise(request, userId);
    }

    private boolean validateUnRegisterEnterprise(RegEnterprise.unRegEnterprise request, String userId) {
        EnterpriseEntity enterprise = enterpriseRepository
                .findByUserIdAndEnterpriseName(userId, request.getEnterpriseName())
                .orElseThrow(() -> new EnterpriseException(ErrorCode.UN_MATH_USERID_ADDRESS));

        if (!enterprise.getEnterprisePassword().equals(request.getEnterprisePassword())) {
            throw new EnterpriseException(ErrorCode.UN_MATH_ENTERPRISE_PASSWORD);
        }

        if (!enterprise.getReservedUser().equals(NOT_EXISTS_USER)) {
            throw new EnterpriseException(ErrorCode.EXISTS_RESERVED_USER);
        }
        
        return true;
    }
}
