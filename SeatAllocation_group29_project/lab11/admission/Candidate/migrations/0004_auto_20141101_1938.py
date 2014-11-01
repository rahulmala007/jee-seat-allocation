# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0003_auto_20141101_0245'),
    ]

    operations = [
        migrations.AlterField(
            model_name='candidate',
            name='question',
            field=models.CharField(default=b'What_is_Your_Birth_Date?', max_length=100),
            preserve_default=True,
        ),
    ]
