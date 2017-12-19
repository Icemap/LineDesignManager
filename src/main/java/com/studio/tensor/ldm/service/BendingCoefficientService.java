package com.studio.tensor.ldm.service;

import com.studio.tensor.ldm.pojo.BendingCoefficientInfo;

public interface BendingCoefficientService
{
	BendingCoefficientInfo getBendingCoefficient(Integer userId);
	Boolean updateBendingCoefficient(BendingCoefficientInfo bendingCoefficientInfo);
	Boolean insertBendingCoefficient(BendingCoefficientInfo bendingCoefficientInfo);
}
