package com.gsww.jup.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 条件接口
 * 用户提供条件表达式接口
 * @Class Name Criterion
 * @Author lee
 * @Create In 2012-2-8
 */
public interface Criterion {
	public enum Operator {
		EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, IN
	}

	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}