package com.wineart.utils

import lombok.NonNull

interface VoidAction<ArgumentY, ArgumentT> {
    fun execute(@NonNull bot: ArgumentY, @NonNull argument: ArgumentT)
}