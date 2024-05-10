package com.rewards.rewardscalculator.exception;

import lombok.Builder;

import java.util.List;

@Builder
public class RewardsValidationErrorResponse {

    private String message;
    private List<String> errors;
}
