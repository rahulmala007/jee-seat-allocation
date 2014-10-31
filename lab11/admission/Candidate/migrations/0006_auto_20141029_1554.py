# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0005_auto_20141029_1151'),
    ]

    operations = [
        migrations.AddField(
            model_name='candidate',
            name='password',
            field=models.CharField(default=b'user', max_length=15),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='candidate',
            name='user_name',
            field=models.CharField(default=b'user', max_length=20),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='category',
            field=models.CharField(default=b'user', max_length=3),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='final_seat',
            field=models.CharField(default=b'0', max_length=10),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='preferences',
            field=models.CharField(default=b'user', max_length=1000),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankGE',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankGEPD',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankOBC',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankOBCPD',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankSC',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankSCPD',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankST',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='rankSTPD',
            field=models.CharField(default=b'0', max_length=5),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='candidate',
            name='roll_no',
            field=models.CharField(default=b'user', max_length=10),
            preserve_default=True,
        ),
    ]
