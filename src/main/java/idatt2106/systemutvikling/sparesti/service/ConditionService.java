package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.ConditionDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneLogDAO;
import idatt2106.systemutvikling.sparesti.enums.ConditionType;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class ConditionService {
  private final MilestoneLogRepository milestoneLogRepository;
  private final MilestoneRepository milestoneRepository;
  private final ChallengeLogRepository challengeLogRepository;

  public ConditionService(MilestoneLogRepository milestoneLogRepository, MilestoneRepository milestoneRepository, ChallengeLogRepository challengeLogRepository) {
    this.milestoneLogRepository = milestoneLogRepository;
    this.milestoneRepository = milestoneRepository;
    this.challengeLogRepository = challengeLogRepository;
  }

  public boolean isConditionMet(ConditionDAO condition) {
    ConditionType type = condition.getConditionType();
    switch (type) {
      case MILESTONES -> {
        return isMilestonesConditionMet(condition);
      }
      case CHALLENGES -> {
        return isChallengesConditionMet(condition);
      }
      case SAVINGS -> {
        return isSavingsConditionMet(condition);
      }
      //TODO make reasonable exception
      default -> throw new IllegalArgumentException();
    }
  }

  private boolean isMilestonesConditionMet(ConditionDAO condition) {
    return condition.getQuantity() == milestoneLogRepository.count();
  }

  private boolean isChallengesConditionMet(ConditionDAO condition) {
    List<ChallengeLogDAO> challengeLogDAOS = challengeLogRepository.findAll();
    Long count = 0L;
    for (ChallengeLogDAO challengeLogDAO : challengeLogDAOS) {
      if (Objects.equals(challengeLogDAO.getGoalSum(), challengeLogDAO.getChallengeAchievedSum())) {
        count++;
      }
    }
    return Objects.equals(condition.getQuantity(), count);
  }

  private boolean isSavingsConditionMet(ConditionDAO condition) {
    List<MilestoneLogDAO> milestoneLogDAOS = milestoneLogRepository.findAll();
    List<MilestoneDAO> milestoneDAOS = milestoneRepository.findAll();
    Long total = 0L;
    for (MilestoneLogDAO milestoneLogDAO : milestoneLogDAOS) {
      total += milestoneLogDAO.getMilestoneAchievedSum();
    }

    for (MilestoneDAO milestoneDAO : milestoneDAOS) {
      total += milestoneDAO.getMilestoneCurrentSum();
    }
    return Objects.equals(condition.getQuantity(), total);
  }
}
