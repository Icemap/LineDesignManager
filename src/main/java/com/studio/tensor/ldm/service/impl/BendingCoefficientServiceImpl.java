package com.studio.tensor.ldm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.dao.BendingCoefficientInfoMapper;
import com.studio.tensor.ldm.pojo.BendingCoefficientInfo;
import com.studio.tensor.ldm.service.BendingCoefficientService;

@Service
public class BendingCoefficientServiceImpl implements BendingCoefficientService
{
	@Autowired
	BendingCoefficientInfoMapper bendingCoefficientInfoMapper;
	
	@Override
	public BendingCoefficientInfo getBendingCoefficient(Integer userId)
	{
		return bendingCoefficientInfoMapper.selectByPrimaryKey(userId);
	}

	@Override
	public Boolean updateBendingCoefficient(BendingCoefficientInfo bendingCoefficientInfo)
	{
		return bendingCoefficientInfoMapper.updateByPrimaryKeySelective(bendingCoefficientInfo) == 1;
	}

	@Override
	public Boolean insertBendingCoefficient(BendingCoefficientInfo bendingCoefficientInfo)
	{
		return bendingCoefficientInfoMapper.insertSelective(bendingCoefficientInfo) == 1;
	}

}
