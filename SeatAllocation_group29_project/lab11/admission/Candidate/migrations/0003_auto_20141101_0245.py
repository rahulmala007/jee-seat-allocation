# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0002_candidate_dob'),
    ]

    operations = [
        migrations.AlterField(
            model_name='candidate',
            name='answer',
            field=models.CharField(default='', max_length=100),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='question',
            field=models.CharField(default='What is Your Birth Date?', max_length=100),
            preserve_default=True,
        ),
    ]
