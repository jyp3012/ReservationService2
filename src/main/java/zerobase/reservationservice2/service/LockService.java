package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import zerobase.reservationservice2.exception.CustomTotalException;
import zerobase.reservationservice2.exception.ErrorCode;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockService {
    private final RedissonClient redissonClient;

    public void Lock(String enterpriseName) {
        RLock lock = redissonClient.getLock(getLockKey(enterpriseName));
        log.debug("Trying lock for enterpriseName : {}", enterpriseName);
        try {
            boolean isLock = lock.tryLock(5, 8, TimeUnit.SECONDS);
            if (!isLock) {
                log.error("====== Lock acquisition failed ======");
                throw new CustomTotalException(ErrorCode.ENTERPRISE_TRANSACTION_LOCK);
            }
        }catch (CustomTotalException e) {
            throw e;
        } catch (Exception e) {
            log.error("Redis lock failed");
        }
    }

    public  void unLock(String enterpriseName) {
        log.debug("UnLock for enterpriseName : {}", enterpriseName);
        redissonClient.getLock(getLockKey(enterpriseName)).unlock();
    }

    private String getLockKey(String enterpriseName) {
        return "ACLK:" + enterpriseName;
    }
}
