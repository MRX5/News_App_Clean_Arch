package com.example.news_app.domain.model.mapper

interface DomainMapper<T, DomainMapper> {

    fun mapToDomainMapper(model: T): DomainMapper
    fun mapFromDomainMapper(model: DomainMapper): T

}