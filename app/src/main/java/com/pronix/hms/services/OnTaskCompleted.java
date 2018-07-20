package com.pronix.hms.services;


import com.pronix.hms.models.WebServiceDO;

/**
 * Created by ravi on 1/11/2018.
 */

public interface OnTaskCompleted {

    void onTaskCompletes(WebServiceDO webServiceDO);
  }
