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
import java.util.logging.Logger;


@Service
public class ConditionService {
  private final MilestoneLogRepository milestoneLogRepository;
  private final MilestoneRepository milestoneRepository;
  private final ChallengeLogRepository challengeLogRepository;
  private final Logger logger = Logger.getLogger(ConditionService.class.getName());


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
    return condition.getQuantity() <= milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(CurrentUserService.getCurrentUsername()).size();
  }

  private boolean isChallengesConditionMet(ConditionDAO condition) {
    List<ChallengeLogDAO> challengeLogDAOS = challengeLogRepository.findChallengeLogDAOByUserDAO_Username(CurrentUserService.getCurrentUsername());
    Long count = 0L;
    for (ChallengeLogDAO challengeLogDAO : challengeLogDAOS) {
      if (challengeLogDAO.getGoalSum() <= challengeLogDAO.getChallengeAchievedSum()) {
        count++;
      }
    }
    return condition.getQuantity() <= count;
  }

  private boolean isSavingsConditionMet(ConditionDAO condition) {
    List<MilestoneLogDAO> milestoneLogDAOS = milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(CurrentUserService.getCurrentUsername());
    List<MilestoneDAO> milestoneDAOS = milestoneRepository.findMilestoneDAOByUserDAO_Username(CurrentUserService.getCurrentUsername());
    Long total = 0L;
    for (MilestoneLogDAO milestoneLogDAO : milestoneLogDAOS) {
      total += milestoneLogDAO.getMilestoneAchievedSum();
    }

    for (MilestoneDAO milestoneDAO : milestoneDAOS) {
      total += milestoneDAO.getMilestoneCurrentSum();
    }
    return condition.getQuantity() <= total;
  }
}
