package com.pinyougou.web.common.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author 邱长海
 */

var Any.logger: Logger
    get() {
        return LoggerFactory.getLogger(this.javaClass)
    }
    private set(value) {}